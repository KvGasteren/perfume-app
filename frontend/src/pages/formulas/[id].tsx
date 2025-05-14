import React, { useEffect, useState } from "react";
import { useRouter } from "next/router";
import { getFormulaById } from "../../services/api";
import { Formula, FormulaIngredient, Allergen } from "@/src/types/perfume";

const FormulaDetails: React.FC = () => {
    const router = useRouter();
    const { id } = router.query;
    const [formula, setFormula] = useState<Formula | null>(null);

    useEffect(() => {
        if (id) {
            fetchFormulaDetails(Number(id));
        }
    }, [id]);

    const fetchFormulaDetails = async (formulaId: number) => {
        const response = await getFormulaById(formulaId);
        console.log("response.data", response)
        setFormula(response);
    };

    const calculateAllergenSummary = () => {
        const allergens: { [key: string]: { totalConcentration: number; maxConcentration: number } } = {};

        formula?.ingredients?.forEach((ingredient: FormulaIngredient) => {
            ingredient.allergens.forEach((allergen: Allergen) => {
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

    return (
        <div className="p-6 max-w-4xl mx-auto">
            {formula ? (
                <>
                    <div className="mb-6">
                        <h1 className="text-3xl font-bold mb-2">{formula.name}</h1>
                        <p className="text-sm text-gray-500">Formula ID: {formula.id}</p>
                    </div>

                    <section className="mb-8">
                        <h2 className="text-xl font-semibold mb-4">Ingredients</h2>
                        <div className="overflow-x-auto rounded-lg border border-gray-200">
                            <table className="min-w-full table-auto text-sm">
                                <thead className="bg-gray-100">
                                    <tr>
                                        <th className="px-4 py-2 text-left">Ingredient</th>
                                        <th className="px-4 py-2 text-left">Concentration (%)</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {formula.ingredients?.map((ingredient) => (
                                        <tr
                                            key={ingredient.id}
                                            className="hover:bg-gray-50 cursor-pointer"
                                            onClick={() => router.push(`/ingredients/${ingredient.id}`)}
                                        >
                                            <td className="px-4 py-2 text-blue-600 underline">{ingredient.name}</td>
                                            <td className="px-4 py-2">{ingredient.concentration.toFixed(2)}</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    </section>

                    <section className="mb-8">
                        <h2 className="text-xl font-semibold mb-4">Allergen Summary</h2>
                        <div className="overflow-x-auto rounded-lg border border-gray-200">
                            <table className="min-w-full table-auto text-sm">
                                <thead className="bg-gray-100">
                                    <tr>
                                        <th className="px-4 py-2 text-left">Allergen</th>
                                        <th className="px-4 py-2 text-left">Total Concentration (%)</th>
                                        <th className="px-4 py-2 text-left">Max Allowed (%)</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {calculateAllergenSummary().map((allergen) => (
                                        <tr
                                            key={allergen.name}
                                            className="hover:bg-gray-50 cursor-pointer"
                                            onClick={() => router.push(`/allergens/${encodeURIComponent(allergen.name)}`)}
                                        >
                                            <td className="px-4 py-2 text-blue-600 underline">{allergen.name}</td>
                                            <td className="px-4 py-2">{allergen.totalConcentration}</td>
                                            <td className="px-4 py-2">{allergen.maxConcentration}</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                        <p className="mt-2 text-xs text-red-500">
                            NB: Total concentration is currently calculated in the frontend
                        </p>
                    </section>

                    <div className="flex justify-end">
                        <button
                            onClick={() => router.push(`/formulas/edit/${formula.id}`)}
                            className="bg-blue-600 hover:bg-blue-700 text-white font-medium px-4 py-2 rounded"
                        >
                            Edit Formula
                        </button>
                    </div>
                </>
            ) : (
                <p className="text-center text-gray-500">Loading formula details...</p>
            )}
        </div>
    );
};

export default FormulaDetails;
