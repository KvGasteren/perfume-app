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
  parts: number;
}

export interface AllergenSummary {
  name: string,
  totalConcentrationInFormula: number;
  maxAllowedConcentration: number;
}

export interface Formula {
  id: number;
  name: string;
  ingredients: FormulaIngredient[];
  allergenSummary?: AllergenSummary[];
}

export interface ApiError {
  error: string;
  message: string;
}