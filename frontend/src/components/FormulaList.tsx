import { useEffect, useState } from "react";
import { useRouter } from "next/router";
import { getFormulas } from "../services/api"; // Replace with your actual API service path

const FormulaList: React.FC = () => {
    const [formulas, setFormulas] = useState<any[]>([]);
    const router = useRouter();

    useEffect(() => {
        fetchFormulas();
    }, []);

    const fetchFormulas = async () => {
        try {
            const response = await getFormulas();
            setFormulas(response.data);
        } catch (error) {
            console.error("Failed to fetch formulas:", error);
        }
    };

    const handleRowClick = (id: number) => {
        router.push(`/formulas/${id}`); // Navigate to the details page
    };

    return (
        <div className="p-4">
            <h2 className="text-xl font-semibold mb-4">Formula List</h2>
            <table className="table-auto border-collapse border border-gray-300 w-full">
                <thead>
                <tr>
                    <th className="border border-gray-300 px-4 py-2">ID</th>
                    <th className="border border-gray-300 px-4 py-2">Name</th>
                </tr>
                </thead>
                <tbody>
                {formulas.map((formula) => (
                    <tr
                        key={formula.id}
                        className="cursor-pointer hover:bg-gray-100"
                        onClick={() => handleRowClick(formula.id)}
                    >
                        <td className="border border-gray-300 px-4 py-2 text-center">
                            {formula.id}
                        </td>
                        <td className="border border-gray-300 px-4 py-2">
                            {formula.name}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default FormulaList;
