import React, { useState } from "react";
import { createFormula } from "../services/api";

const FormulaForm: React.FC = () => {
    const [name, setName] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (name) {
            await createFormula({ name });
            setName("");
        }
    };

    return (
        <form onSubmit={handleSubmit} className="flex flex-col space-y-4">
            <input
                type="text"
                className="border p-2"
                placeholder="Formula Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
            />
            <button type="submit" className="bg-primary text-white p-2">
                Add Formula
            </button>
        </form>
    );
};

export default FormulaForm;
