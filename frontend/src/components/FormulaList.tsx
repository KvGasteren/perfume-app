import React, { useEffect, useState } from "react";
import { getFormulas } from "../services/api";

const FormulaList: React.FC = () => {
    const [formulas, setFormulas] = useState<any[]>([]);

    useEffect(() => {
        fetchFormulas();
    }, []);

    const fetchFormulas = async () => {
        const response = await getFormulas();
        setFormulas(response.data);
    };

    return (
        <div>
            <h2 className="text-xl font-semibold">Formula List</h2>
            <ul className="list-disc list-inside">
                {formulas.map((formula) => (
                    <li key={formula.id}>{formula.name}</li>
                ))}
            </ul>
        </div>
    );
};

export default FormulaList;
