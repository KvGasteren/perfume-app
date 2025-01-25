import React, { useEffect, useState } from "react";
import { useRouter } from "next/router";
import { getFormulaById } from "../../services/api"; // Adjust this to your API service

const FormulaDetails: React.FC = () => {
    const router = useRouter();
    const { id } = router.query;
    const [formula, setFormula] = useState<any | null>(null);

    useEffect(() => {
        if (id) {
            fetchFormulaDetails(Number(id));
        }
    }, [id]);

    const fetchFormulaDetails = async (formulaId: number) => {
        const response = await getFormulaById(formulaId); // API call to fetch formula details by ID
        setFormula(response.data);
    };

    const calculateAllergenSummary = () => {
        const allergens: { [key: string]: { totalConcentration: number; maxConcentration: number } } = {};

        formula?.ingredients.forEach((ingredient: any) => {
            ingredient.allergens.forEach((allergen: any) => {
                if (!allergens[allergen.name]) {
                    allergens[allergen.name] = {
                        totalConcentration: 0,
                        maxConcentration: allergen.maxConcentration,
                    };
                }
                allergens[allergen.name].totalConcentration += allergen.concentration;
            });
        });

        return Object.entries(allergens).map(([name, data]) => ({
            name,
            totalConcentration: data.totalConcentration.toFixed(2),
            maxConcentration: data.maxConcentration.toFixed(2),
        }));
    };

    return (
        <div className="p-4">
            {formula ? (
                <>
                    <h1 className="text-2xl font-bold mb-4">{formula.name}</h1>

                    <h2 className="text-xl font-semibold mb-2">Ingredients</h2>
                    <table className="table-auto w-full border-collapse border border-gray-200 mb-4">
                        <thead>
                        <tr>
                            <th className="border border-gray-300 px-4 py-2">Ingredient</th>
                            <th className="border border-gray-300 px-4 py-2">Concentration (%)</th>
                        </tr>
                        </thead>
                        <tbody>
                        {formula.ingredients.map((ingredient: any) => (
                            <tr key={ingredient.id}>
                                <td className="border border-gray-300 px-4 py-2">{ingredient.name}</td>
                                <td className="border border-gray-300 px-4 py-2">{ingredient.concentration.toFixed(2)}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>

                    <h2 className="text-xl font-semibold mb-2">Allergens Summary</h2>
                    <table className="table-auto w-full border-collapse border border-gray-200">
                        <thead>
                        <tr>
                            <th className="border border-gray-300 px-4 py-2">Allergen</th>
                            <th className="border border-gray-300 px-4 py-2">Total Concentration (%)</th>
                            <th className="border border-gray-300 px-4 py-2">Max Allowed (%)</th>
                        </tr>
                        </thead>
                        <tbody>
                        {calculateAllergenSummary().map((allergen) => (
                            <tr key={allergen.name}>
                                <td className="border border-gray-300 px-4 py-2">{allergen.name}</td>
                                <td className="border border-gray-300 px-4 py-2">{allergen.totalConcentration}</td>
                                <td className="border border-gray-300 px-4 py-2">{allergen.maxConcentration}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </>
            ) : (
                <p>Loading...</p>
            )}
        </div>
    );
};

export default FormulaDetails;
