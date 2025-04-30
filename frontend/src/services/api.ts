import axios from 'axios';
import { Allergen, Formula, Ingredient } from '../types/perfume';

const API = axios.create({ baseURL: 'http://localhost:8080/api' });

// Formulas
export const getFormulas = () => API.get('/formulas');
export const createFormula = (data: Formula) => API.post('/formulas', data);
export const updateFormula = (id: number, data: Formula) => API.put(`/formulas/${id}`, data);
export const deleteFormula = (id: number) => API.delete(`/formulas/${id}`);
export const getFormulaById = (formulaId: number): Promise<Formula> => API.get(`formulas/${formulaId}`);

// Ingredients
export const getIngredients = () => API.get('/ingredients');
export const getIngredientById = (id: number) => API.get(`/ingredients/${id}`);
export const createIngredient = (data: Ingredient) => API.post('/ingredients', data);
export const updateIngredient = (id: number, data: Ingredient) => API.put(`/ingredients/${id}`, data);
export const deleteIngredient = (id: number) => API.delete(`/ingredients/${id}`);

// Allergens
export const addAllergen = (ingredientId: number, data: Allergen) =>
    API.post(`/ingredients/${ingredientId}/allergens`, data);
export const removeAllergen = (ingredientId: number, allergenId: number) =>
    API.delete(`/ingredients/${ingredientId}/allergens/${allergenId}`);
export const getAllergens = () => API.get('/allergens');
export const updateAllergen = (id: number, updatedData: {name: string, maxConcentration: number}) => {
    return API.put(`/allergens/${id}`, updatedData);
}
export const deleteAllergen = (id: number) => API.delete(`/allergens/${id}`)
export const createAllergen = (allergen: { name: string }) => {
    return API.post('/allergens', allergen)
}