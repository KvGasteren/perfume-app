import React, { useEffect, useState } from "react";
import { useRouter } from "next/router";
import { getFormulaById } from "../../services/api";
import { Formula, FormulaIngredient, Allergen } from "@/src/types/perfume";

const FormulaDetails: React.FC = () => {
  const router = useRouter();
  const { id } = router.query;
  const [formula, setFormula] = useState<Formula | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (typeof id === "string") {
      fetchFormulaDetails(Number(id));
    }
  }, [id]);

  const fetchFormulaDetails = async (formulaId: number) => {
    try {
      setLoading(true);
      const response = await getFormulaById(formulaId);
      setFormula(response);
    } finally {
      setLoading(false);
    }
  };

  const calculateAllergenSummary = () => {
    const allergens: {
      [key: string]: { totalConcentration: number; maxConcentration: number };
    } = {};

    formula?.ingredients?.forEach((ingredient: FormulaIngredient) => {
      ingredient.allergens.forEach((allergen: Allergen) => {
        if (!allergens[allergen.name]) {
          allergens[allergen.name] = {
            totalConcentration: 0,
            maxConcentration:
              allergen.maxConcentration * ingredient.concentration,
          };
        }
        allergens[allergen.name].totalConcentration +=
          allergen.concentration * ingredient.concentration;
      });
    });

    return Object.entries(allergens).map(([name, data]) => ({
      name,
      totalConcentration: data.totalConcentration.toFixed(2),
      maxConcentration: data.maxConcentration.toFixed(2),
    }));
  };

  return (
    <div className="max-w-4xl mx-auto">
      {loading ? (
        <p>Loading...</p>
      ) : formula ? (
        <>
          <div className="mb-4">
            <label className="block text-sm font-medium mb-1">Name</label>
            <p className="border border-gray-300 rounded px-2 py-1 bg-gray-50">
              {formula.name}
            </p>
          </div>

          <div className="mb-6">
            <label className="block text-sm font-medium mb-2">Ingredients</label>
            <table className="table-auto border-collapse border border-gray-300 w-full text-sm">
              <thead className="bg-gray-100">
                <tr>
                  <th className="border border-gray-300 px-4 py-2 text-left">Name</th>
                  <th className="border border-gray-300 px-4 py-2 text-left">Concentration (%)</th>
                </tr>
              </thead>
              <tbody>
                {formula.ingredients.map((ingredient) => (
                  <tr
                    key={ingredient.id}
                    className="hover:bg-gray-50 cursor-pointer"
                    onClick={() => router.push(`/ingredients/${ingredient.id}`)}
                  >
                    <td className="border border-gray-300 px-4 py-2">
                      {ingredient.name}
                    </td>
                    <td className="border border-gray-300 px-4 py-2">
                      {ingredient.concentration.toFixed(2)}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          <div>
            <label className="block text-sm font-medium mb-2">Allergen Summary</label>
            <table className="table-auto border-collapse border border-gray-300 w-full text-sm">
              <thead className="bg-gray-100">
                <tr>
                  <th className="border border-gray-300 px-4 py-2 text-left">Allergen</th>
                  <th className="border border-gray-300 px-4 py-2 text-left">Total Concentration (%)</th>
                  <th className="border border-gray-300 px-4 py-2 text-left">Max Allowed (%)</th>
                </tr>
              </thead>
              <tbody>
                {calculateAllergenSummary().map((allergen) => (
                  <tr
                    key={allergen.name}
                    className="hover:bg-gray-50 cursor-pointer"
                    onClick={() =>
                      router.push(`/allergens/${encodeURIComponent(allergen.name)}`)
                    }
                  >
                    <td className="border border-gray-300 px-4 py-2">
                      {allergen.name}
                    </td>
                    <td className="border border-gray-300 px-4 py-2">{allergen.totalConcentration}</td>
                    <td className="border border-gray-300 px-4 py-2">{allergen.maxConcentration}</td>
                  </tr>
                ))}
              </tbody>
            </table>
            <p className="text-xs text-red-500 mt-2">
              NB: Total concentration is currently calculated in the frontend.
            </p>
          </div>
        </>
      ) : (
        <p>Formula not found.</p>
      )}
    </div>
  );
};

export default FormulaDetails;
