import React, { useEffect, useState } from "react";
import { getAllergens } from "../services/api";

const AllergenList: React.FC = () => {
    const [allergens, setAllergens] = useState<any[]>([]);

    useEffect(() => {
        fetchAllergens();
    }, []);

    const fetchAllergens = async () => {
        const response = await getAllergens();
        console.log(response.data);
        setAllergens(response.data);
    };

    return (
        <div>
            <h2 className="text-xl font-semibold">Allergen List</h2>
            <ul className="list-disc list-inside">
                {allergens.map((allergen) => (
                    <li key={allergen.id}>{allergen.id}: {allergen.name} - {allergen.maxConcentration}</li>
                ))}
            </ul>
        </div>
    );
};

export default AllergenList;
