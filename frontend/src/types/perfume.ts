export interface Allergen {
  id: number;
  name: string;
  concentration: number;
  maxConcentration: number;
}

export interface IngredientAllergen extends Allergen {
  concentration: number;
}


export interface Ingredient {
  id: number;
  name: string;
  allergens: IngredientAllergen[];
}

export interface FormulaIngredient extends Ingredient {
  concentration: number;
}

export interface Formula {
  id: number;
  name: string;
  ingredients: FormulaIngredient[];
}

export interface ApiError {
  error: string;
  message: string;
}