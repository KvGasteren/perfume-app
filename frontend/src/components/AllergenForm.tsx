import React, { useState } from "react";
import { addAllergen } from "../services/api";

interface AllergenFormProps {
    ingredientId: number;
}

const AllergenForm: React.FC<AllergenFormProps> = ({ ingredientId }) => {
    const [name, setName] = useState("");
    const [maxConcentration, setMaxConcentration] = useState<number | "">("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (name && maxConcentration) {
            await addAllergen(ingredientId, { name, maxConcentration });
            setName("");
            setMaxConcentration("");
        }
    };

    return (
        <form onSubmit={handleSubmit} className="flex flex-col space-y-4">
            <input
                type="text"
                className="border p-2"
                placeholder="Allergen Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
            />
            <input
                type="number"
                className="border p-2"
                placeholder="Max Concentration"
                value={maxConcentration}
                onChange={(e) => setMaxConcentration(parseFloat(e.target.value))}
            />
            <button type="submit" className="bg-red-500 text-white p-2">
                Add Allergen
            </button>
        </form>
    );
};

export default AllergenForm;
