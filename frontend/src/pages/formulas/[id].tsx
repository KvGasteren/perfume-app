import React, { useEffect, useState } from "react";
import { useRouter } from "next/router";
import { getFormulas } from "../../services/api";

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
        const response = await getFormulas();
        const data = response.data.find((f: any) => f.id === formulaId);
        setFormula(data);
    };

    return (
        <div className="p-4">
            {formula ? (
                <>
                    <h1 className="text-2xl font-bold">{formula.name}</h1>
                    <p>Details about this formula...</p>
                </>
            ) : (
                <p>Loading...</p>
            )}
        </div>
    );
};

export default FormulaDetails;
