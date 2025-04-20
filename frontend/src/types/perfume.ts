export interface Allergen {
  id: number;
  name: string;
  concentration: number;
  maxConcentration: number;
}

export interface Ingredient {
  id: number;
  name: string;
  parts: number;
  concentration: number;
  allergens: Allergen[];
}

export interface Formula {
  id: number;
  name: string;
  ingredients: Ingredient[];
}

export interface ApiError {
  error: string;
  message: string;
}