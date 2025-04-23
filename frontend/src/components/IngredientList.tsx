import React, { useEffect, useState } from "react";
import { getIngredients } from "../services/api";

const IngredientList: React.FC = () => {
    const [ingredients, setIngredients] = useState<any[]>([]);

    useEffect(() => {
        fetchIngredients();
    }, []);

    const fetchIngredients = async () => {
        const response = await getIngredients();
        setIngredients(response.data);
    };

    return (
        <div className="p-4">
            <table className="table-auto border-collapse border border-gray-300 w-full">
                <thead>
                    <tr>
                        <th className="border border-gray-300 px-4 py-2">#</th>
                        <th className="border border-gray-300 px-4 py-2">Name</th>
                        <th className="border border-gray-300 px-4 py-2"># Allergens</th>
                    </tr>
                </thead>
                <tbody>
                {ingredients.map((ingredient) => (
                    <tr key={ingredient.id} 
                        className="cursor-pointer hover:bg-gray-100"
                        /*onClick={() => handleRowClick(ingredient.id)}*/>
                        <td className="border border-gray-300 px-4 py-2 text-center">{ingredient.id}</td>
                        <td className="border border-gray-300 px-4 py-2">{ingredient.name}</td>
                        <td className="border border-gray-300 px-4 py-2">{ingredient.allergens.length}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>

    );
};

export default IngredientList;
