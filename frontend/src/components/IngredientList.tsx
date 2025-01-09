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
        <div>
            <h2 className="text-xl font-semibold">Ingredient List</h2>
            <ul className="list-disc list-inside">
                {ingredients.map((ingredient) => (
                    <li key={ingredient.id}>{ingredient.name}</li>
                ))}
            </ul>
        </div>
    );
};

export default IngredientList;
