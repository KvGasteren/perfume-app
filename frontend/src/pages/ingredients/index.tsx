import React from "react";
import IngredientList from "../../components/IngredientList";

const IngredientsPage: React.FC = () => {
    return (
        <div className="p-4">
            <h1 className="text-2xl font-bold">Ingredients</h1>
            <IngredientList />
        </div>
    );
};

export default IngredientsPage;
