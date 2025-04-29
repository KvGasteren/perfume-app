// import React, { useState } from "react";
// import IngredientList from "../../components/IngredientList";
// import { PlusCircleIcon } from "@heroicons/react/24/solid";

// const IngredientsPage: React.FC = () => {
//   const [ showModal, setShowModal ] = useState(false);
//     return (
//         <div className="p-4">
//           <h1 className="text-2xl font-bold">Ingredients</h1>
//           <IngredientList />
//           <div className="pr-4 flex justify-end mt-4">
//             <button onClick={() => setShowModal(true)} aria-label="Add Allergen" >
//               <PlusCircleIcon className="h-6 w-6 text-primary hover:text-primary/70 cursor-pointer" />
//             </button>
//           </div>
    
//           <AddIngredientModal
//             isOpen={showModal}
//             onClose={() => setShowModal(false)}
//             onAdd={handleAddAllergen}
//             availableAllergens={allergens}
//           />
//         </div>
//       )
// };

// export default IngredientsPage;


import React, { useEffect, useState } from "react";
import IngredientList from "../../components/IngredientList";
import { PlusCircleIcon } from "@heroicons/react/24/solid";
import AddIngredientModal from "../../components/AddIngredientModal";
import { createIngredient, getAllergens } from "../../services/api"; // <-- import your service
import { Allergen, Ingredient, IngredientAllergen } from "@/src/types/perfume";



const IngredientsPage: React.FC = () => {
  const [showModal, setShowModal] = useState(false);
  const [allergens, setAllergens] = useState<Allergen[]>([]);

  useEffect(() => {
    fetchAllergens();
  }, []);

  const fetchAllergens = async () => {
    try {
      const response = await getAllergens();
      setAllergens(response.data);
    } catch (error) {
      console.error("Failed to fetch allergens", error);
    }
  };

  const handleAddIngredient = async (name: string, selectedAllergens: IngredientAllergen[]) => {
    const newIngredient: Ingredient = {
      id: 0, // or omit ID if your backend generates it
      name,
      allergens: selectedAllergens,
    };

    try {
      await createIngredient(newIngredient);
      setShowModal(false);
      // optionally: refresh IngredientList here
    } catch (error) {
      console.error("Failed to create ingredient", error);
    }
  };

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">Ingredients</h1>

      <IngredientList />

      <div className="pr-4 flex justify-end mt-4">
        <button onClick={() => setShowModal(true)} aria-label="Add Ingredient">
          <PlusCircleIcon className="h-6 w-6 text-primary hover:text-primary/70 cursor-pointer" />
        </button>
      </div>

      <AddIngredientModal
        isOpen={showModal}
        onClose={() => setShowModal(false)}
        onAdd={handleAddIngredient}
        availableAllergens={allergens}
      />
    </div>
  );
};

export default IngredientsPage;
