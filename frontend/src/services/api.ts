import axios from 'axios';

const API = axios.create({ baseURL: 'http://localhost:8080/api' });

// Formulas
export const getFormulas = () => API.get('/formulas');
export const createFormula = (data: any) => API.post('/formulas', data);
export const updateFormula = (id: number, data: any) => API.put(`/formulas/${id}`, data);
export const deleteFormula = (id: number) => API.delete(`/formulas/${id}`);

// Ingredients
export const getIngredients = () => API.get('/ingredients');
export const createIngredient = (data: any) => API.post('/ingredients', data);
export const updateIngredient = (id: number, data: any) => API.put(`/ingredients/${id}`, data);
export const deleteIngredient = (id: number) => API.delete(`/ingredients/${id}`);

// Allergens
export const addAllergen = (ingredientId: number, data: any) =>
    API.post(`/ingredients/${ingredientId}/allergens`, data);
export const removeAllergen = (ingredientId: number, allergenId: number) =>
    API.delete(`/ingredients/${ingredientId}/allergens/${allergenId}`);
export const getAllergens = () => API.get('/allergens');