import React, { useEffect, useState } from "react";
import { useRouter } from "next/router";
import { getFormulaById, getIngredients, updateFormula } from "../../services/api";
import { Formula, FormulaIngredient, Allergen, Ingredient } from "@/src/types/perfume";
import { CheckCircleIcon, PencilIcon, XCircleIcon, PlusCircleIcon, TrashIcon } from "@heroicons/react/24/outline";

const FormulaDetails: React.FC = () => {
  const router = useRouter();
  const { id } = router.query;
  const [formula, setFormula] = useState<Formula | null>(null);
  const [originalFormula, setOriginalFormula] = useState<Formula | null>(null);
  const [allIngredients, setAllIngredients] = useState<Ingredient[]>([]);
  const [isEditing, setIsEditing] = useState(false);
  const [name, setName] = useState("");

  useEffect(() => {
    if (typeof id === "string") {
      fetchData(Number(id));
    }
  }, [id]);

  const fetchData = async (formulaId: number) => {
    const [formulaRes, ingredientsRes] = await Promise.all([
      getFormulaById(formulaId),
      getIngredients(),
    ]);
    setFormula(formulaRes);
    setOriginalFormula(formulaRes);
    setName(formulaRes.name);
    setAllIngredients(ingredientsRes.data);
  };

  const updateParts = (index: number, value: number) => {
    if (!formula) return;
    const updated = [...formula.ingredients];
    updated[index].parts = value;
    recalculateConcentration(updated);
  };

  const recalculateConcentration = (ingredients: FormulaIngredient[]) => {
    const totalParts = ingredients.reduce((sum, i) => sum + i.parts, 0);
    const updated = ingredients.map(i => ({
      ...i,
      concentration: totalParts === 0 ? 0 : i.parts / totalParts
    }));
    setFormula(f => f ? { ...f, ingredients: updated } : null);
  };

  const removeIngredient = (index: number) => {
    if (!formula) return;
    const updated = formula.ingredients.filter((_, i) => i !== index);
    recalculateConcentration(updated);
  };

  const addIngredient = (ingredient: Ingredient, parts: number) => {
    if (!formula) return;
    const alreadyIncluded = formula.ingredients.find(i => i.id === ingredient.id);
    if (alreadyIncluded) return;
    const newEntry = {
      id: ingredient.id,
      name: ingredient.name,
      parts,
      concentration: 0,
      allergens: ingredient.allergens || []
    };
    const updated = [...formula.ingredients, newEntry];
    recalculateConcentration(updated);
  };

  const calculateAllergenSummary = () => {
    const allergens: { [key: string]: { totalConcentration: number; maxConcentration: number } } = {};
    formula?.ingredients?.forEach((ingredient) => {
      ingredient.allergens.forEach((allergen) => {
        if (!allergens[allergen.name]) {
          allergens[allergen.name] = {
            totalConcentration: 0,
            maxConcentration: allergen.maxConcentration * ingredient.concentration,
          };
        }
        allergens[allergen.name].totalConcentration += allergen.concentration * ingredient.concentration;
      });
    });
    return Object.entries(allergens).map(([name, data]) => ({
      name,
      totalConcentration: data.totalConcentration.toFixed(2),
      maxConcentration: data.maxConcentration.toFixed(2),
    }));
  };

  const saveChanges = async () => {
    if (!formula) return;
    await updateFormula(formula.id, {
      ...formula,
      name: name.trim(),
    });
    setIsEditing(false);
    fetchData(formula.id);
  };

  const cancelEdit = () => {
    setFormula(originalFormula);
    setIsEditing(false);
  };

  const availableIngredients = allIngredients.filter(
    (i) => !formula?.ingredients.find(fi => fi.id === i.id)
  );

  return (
    <div className="max-w-4xl mx-auto">
      {formula ? (
        <>
          <div className="mb-4">
            <label className="block text-sm font-medium mb-1">Name</label>
            {isEditing ? (
              <input
                value={name}
                onChange={(e) => setName(e.target.value)}
                className="w-full border border-gray-300 rounded px-2 py-1"
              />
            ) : (
              <p className="border border-gray-300 rounded px-2 py-1 bg-gray-50">{formula.name}</p>
            )}
          </div>

          <div className="mb-6">
            <label className="block text-sm font-medium mb-2">Ingredients</label>
            <table className="table-auto border-collapse border border-gray-300 w-full text-sm">
              <thead className="bg-gray-100">
                <tr>
                  <th className="border px-4 py-2 text-left">Name</th>
                  {isEditing && <th className="border px-4 py-2 text-left">Parts</th>}
                  <th className="border px-4 py-2 text-left">Concentration (%)</th>
                  {isEditing && <th className="border px-4 py-2"></th>}
                </tr>
              </thead>
              <tbody>
                {formula.ingredients.map((ingredient, index) => (
                  <tr key={ingredient.id}>
                    <td className="border px-4 py-2">{ingredient.name}</td>
                    {isEditing && (
                      <td className="border px-4 py-2">
                        <input
                          type="number"
                          value={ingredient.parts}
                          onChange={(e) => updateParts(index, Number(e.target.value))}
                          className="w-full border border-gray-300 rounded px-2 py-1"
                        />
                      </td>
                    )}
                    <td className="border px-4 py-2">{(ingredient.concentration * 100).toFixed(2)}</td>
                    {isEditing && (
                      <td className="border px-4 py-2 text-red-500 text-sm cursor-pointer" onClick={() => removeIngredient(index)}>
                        <TrashIcon className="h-5 w-5" />
                      </td>
                    )}
                  </tr>
                ))}
              </tbody>
            </table>

            {isEditing && (
              <div className="mt-4">
                <label className="block text-sm font-medium mb-1">Add Ingredient</label>
                {availableIngredients.length === 0 ? (
                  <p className="text-sm text-gray-500">All ingredients already added.</p>
                ) : (
                  <div className="flex gap-2">
                    <select
                      className="border border-gray-300 rounded px-2 py-1 w-1/2"
                      id="ingredientSelect"
                    >
                      {availableIngredients.map((ing) => (
                        <option key={ing.id} value={ing.id}>{ing.name}</option>
                      ))}
                    </select>
                    <input
                      type="number"
                      className="border border-gray-300 rounded px-2 py-1 w-1/4"
                      placeholder="Parts"
                      id="ingredientParts"
                    />
                    <button
                      onClick={() => {
                        const sel = document.getElementById("ingredientSelect") as HTMLSelectElement;
                        const val = document.getElementById("ingredientParts") as HTMLInputElement;
                        const selected = allIngredients.find(i => i.id === Number(sel.value));
                        if (selected) addIngredient(selected, Number(val.value));
                      }}
                      className="text-primary hover:text-primary/70"
                      title="Add"
                    >
                      <PlusCircleIcon className="h-6 w-6" />
                    </button>
                  </div>
                )}
              </div>
            )}
          </div>

          <div className="mb-6">
            <label className="block text-sm font-medium mb-2">Allergen Summary</label>
            <table className="table-auto border-collapse border border-gray-300 w-full text-sm">
              <thead className="bg-gray-100">
                <tr>
                  <th className="border px-4 py-2 text-left">Allergen</th>
                  <th className="border px-4 py-2 text-left">Total Concentration (%)</th>
                  <th className="border px-4 py-2 text-left">Max Allowed (%)</th>
                </tr>
              </thead>
              <tbody>
                {calculateAllergenSummary().map((allergen) => (
                  <tr key={allergen.name}>
                    <td className="border px-4 py-2">{allergen.name}</td>
                    <td className="border px-4 py-2">{allergen.totalConcentration}</td>
                    <td className="border px-4 py-2">{allergen.maxConcentration}</td>
                  </tr>
                ))}
              </tbody>
            </table>
            <p className="text-xs text-red-500 mt-2">NB: Total concentration is currently calculated in the frontend.</p>
          </div>

          <div className="flex justify-end gap-4 mt-4">
            {isEditing ? (
              <>
                <button title="Save" onClick={saveChanges}>
                  <CheckCircleIcon className="h-6 w-6 text-green-600 hover:text-green-700" />
                </button>
                <button title="Cancel" onClick={cancelEdit}>
                  <XCircleIcon className="h-6 w-6 text-gray-500 hover:text-gray-700" />
                </button>
              </>
            ) : (
              <button title="Edit" onClick={() => setIsEditing(true)}>
                <PencilIcon className="h-6 w-6 text-primary hover:text-primary/70" />
              </button>
            )}
          </div>
        </>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
};

export default FormulaDetails;