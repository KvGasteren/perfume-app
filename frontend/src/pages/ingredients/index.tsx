import React from "react";
import IngredientList from "../../components/IngredientList";
import IngredientForm from "../../components/IngredientForm";

const IngredientsPage: React.FC = () => {
    return (
        <div className="p-4">
            <h1 className="text-2xl font-bold">Ingredients</h1>
            <div className="my-4">
                <IngredientForm />
            </div>
            <IngredientList />
        </div>
    );
};

export default IngredientsPage;
