import React, { useEffect, useState } from "react";
import { useRouter } from "next/router";
import {getAllergens, getFormulas} from "../../services/api";

const AllergenDetails: React.FC = () => {
    const router = useRouter();
    const { id } = router.query;
    const [allergen, setAllergen] = useState<any | null>(null);

    useEffect(() => {
        if (id) {
            fetchAllergenDetails(Number(id));
        }
    }, [id]);

    const fetchAllergenDetails = async (allegenId: number) => {
        const response = await getAllergens();
        const data = response.data.find((a: any) => a.id === allegenId);
        setAllergen(data);
    };

    return (
        <div className="p-4">
            {allergen ? (
                <>
                    <h1 className="text-2xl font-bold">{allergen.name}</h1>
                    <p>Details about this allergen...</p>
                </>
            ) : (
                <p>Loading...</p>
            )}
        </div>
    );
};

export default AllergenDetails;
