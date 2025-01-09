import React, { useState } from "react";
import { createIngredient } from "../services/api";

const IngredientForm: React.FC = () => {
    const [name, setName] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (name) {
            await createIngredient({ name });
            setName("");
        }
    };

    return (
        <form onSubmit={handleSubmit} className="flex flex-col space-y-4">
            <input
                type="text"
                className="border p-2"
                placeholder="Ingredient Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
            />
            <button type="submit" className="bg-green-500 text-white p-2">
                Add Ingredient
            </button>
        </form>
    );
};

export default IngredientForm;
