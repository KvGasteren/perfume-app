import React, { useEffect, useState } from "react";
import { useRouter } from "next/router";
import { getIngredients } from "../../services/api";

const IngredientDetails: React.FC = () => {
    const router = useRouter();
    const { id } = router.query;
    const [ingredient, setIngredient] = useState<any | null>(null);

    useEffect(() => {
        if (id) {
            fetchIngredientDetails(Number(id));
        }
    }, [id]);

    const fetchIngredientDetails = async (ingredientId: number) => {
        const response = await getIngredients();
        const data = response.data.find((i: any) => i.id === ingredientId);
        setIngredient(data);
    };

    return (
        <div className="p-4">
            {ingredient ? (
                <>
                    <h1 className="text-2xl font-bold">{ingredient.name}</h1>
                    <p>Details about this ingredient...</p>
                </>
            ) : (
                <p>Loading...</p>
            )}
        </div>
    );
};

export default IngredientDetails;
