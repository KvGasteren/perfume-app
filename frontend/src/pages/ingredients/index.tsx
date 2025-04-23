import React from "react";
import IngredientList from "../../components/IngredientList";
import { PlusCircleIcon } from "@heroicons/react/24/solid";

const IngredientsPage: React.FC = () => {
    return (
        <div className="p-4">
          <h1 className="text-2xl font-bold">Ingredients</h1>
          <IngredientList />
          <div className="pr-4 flex justify-end mt-4">
            <button /*onClick={() => setShowModal(true)} aria-label="Add Allergen"*/ >
              <PlusCircleIcon className="h-6 w-6 text-primary hover:text-primary/70 cursor-pointer" />
            </button>
          </div>
    
          {/* <AddAllergenModal
            isOpen={showModal}
            onClose={() => setShowModal(false)}
            onAdd={handleAddAllergen}
          /> */}
        </div>
      )
};

export default IngredientsPage;
