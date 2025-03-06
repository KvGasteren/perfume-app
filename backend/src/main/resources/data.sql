DELETE FROM formula_ingredient WHERE TRUE;
DELETE FROM ingredient_allergen WHERE TRUE;
DELETE FROM allergen WHERE TRUE;
DELETE FROM formula WHERE TRUE;
DELETE FROM ingredient WHERE TRUE;

-- Insert sample allergens
INSERT INTO allergen (id, name, max_concentration) VALUES
(1, 'Linalool', 0.1),
(2, 'Citronellol', 0.05),
(3, 'Geraniol', 0.08);

-- Insert sample ingredients
INSERT INTO ingredient (id, name) VALUES
(1, 'Rose Oil'),
(2, 'Lavender Oil'),
(3, 'Lemon Extract');

-- Insert sample formulas
INSERT INTO formula (id, name) VALUES
(1, 'Fresh Citrus'),
(2, 'Floral Bouquet');

-- Insert IngredientAllergen relationships with concentration information
INSERT INTO ingredient_allergen (id, ingredient_id, allergen_id, concentration) VALUES
(1, 1, 1, 0.02), -- Rose Oil contains Linalool at 0.02 concentration
(2, 1, 2, 0.03), -- Rose Oil contains Citronellol at 0.03 concentration
(3, 2, 1, 0.04), -- Lavender Oil contains Linalool at 0.04 concentration
(4, 3, 3, 0.01); -- Lemon Extract contains Geraniol at 0.01 concentration

-- Insert FormulaIngredient relationships with concentration information
INSERT INTO formula_ingredient (id, formula_id, ingredient_id, concentration) VALUES
(1, 1, 3, 0.5), -- Fresh Citrus formula contains Lemon Extract at 0.5 concentration
(2, 1, 2, 0.3), -- Fresh Citrus formula contains Lavender Oil at 0.3 concentration
(3, 2, 1, 0.6), -- Floral Bouquet formula contains Rose Oil at 0.6 concentration
(4, 2, 2, 0.4); -- Floral Bouquet formula contains Lavender Oil at 0.4 concentration

