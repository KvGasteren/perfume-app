import React, {
    useEffect,
    useState,
    useImperativeHandle,
    forwardRef,
  } from "react";
  import { getIngredients } from "../services/api";
  import { Ingredient } from "../types/perfume";
  import { useRouter } from "next/router";
  
  export interface IngredientListRef {
    refresh: () => void;
  }
  
  const IngredientList = forwardRef<IngredientListRef>((_, ref) => {
    const [ingredients, setIngredients] = useState<Ingredient[]>([]);
    const router = useRouter();
  
    const fetchIngredients = async () => {
      const response = await getIngredients();
      setIngredients(response.data);
    };
  
    useImperativeHandle(ref, () => ({
      refresh: fetchIngredients,
    }));
  
    useEffect(() => {
      fetchIngredients();
    }, []);
  
    const handleRowClick = (id: number) => {
      router.push(`/ingredients/${id}`);
    };
  
    return (
      <div className="p-4">
        <table className="table-auto border-collapse border border-gray-300 w-full">
          <thead>
            <tr>
              <th className="border border-gray-300 px-4 py-2">#</th>
              <th className="border border-gray-300 px-4 py-2">Name</th>
              <th className="border border-gray-300 px-4 py-2"># Allergens</th>
            </tr>
          </thead>
          <tbody>
            {ingredients.map((ingredient) => (
              <tr
                key={ingredient.id}
                className="cursor-pointer hover:bg-gray-100"
                onClick={() => handleRowClick(ingredient.id)}
              >
                <td className="border border-gray-300 px-4 py-2 text-center">
                  {ingredient.id}
                </td>
                <td className="border border-gray-300 px-4 py-2">
                  {ingredient.name}
                </td>
                <td className="border border-gray-300 px-4 py-2">
                  {ingredient.allergens.length}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  });
  
  IngredientList.displayName = "IngredientList";
  export default IngredientList;
  