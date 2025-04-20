import React, { useEffect, useState } from "react";
import { getAllergens } from "../services/api";
import { Allergen } from "../types/perfume";
import { useRouter } from "next/router";

const AllergenList: React.FC = () => {
    const [allergens, setAllergens] = useState<Allergen[]>([]);
    const router = useRouter();

    useEffect(() => {
        fetchAllergens();
    }, []);

    const fetchAllergens = async () => {
        const response = await getAllergens();
        // console.log(response.data);
        setAllergens(response.data);
    };

    const handleRowClick = (id: number) => {
        router.push(`/allergens/${id}`); // Navigate to the details page
    };

    return (
        <div className="p-4">
            <table className="table-auto border-collapse border border-gray-300 w-full">
                <thead>
                    <tr>
                        <th className="border border-gray-300 px-4 py-2">#</th>
                        <th className="border border-gray-300 px-4 py-2">Name</th>
                        <th className="border border-gray-300 px-4 py-2">Max Concentration (%)</th>
                    </tr>
                </thead>
                <tbody>
                {allergens.map((allergen) => (
                    <tr key={allergen.id} 
                        className="cursor-pointer hover:bg-gray-100"
                        onClick={() => handleRowClick(allergen.id)}>
                        <td className="border border-gray-300 px-4 py-2 text-center">{allergen.id}</td>
                        <td className="border border-gray-300 px-4 py-2">{allergen.name}</td>
                        <td className="border border-gray-300 px-4 py-2">{allergen.maxConcentration}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>

    );
};

export default AllergenList;
