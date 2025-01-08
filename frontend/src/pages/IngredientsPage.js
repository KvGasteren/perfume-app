import React, { useEffect, useState } from "react";
import {
  Box,
  Button,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from "@mui/material";

const IngredientsPage = () => {
  const [ingredients, setIngredients] = useState([]);

  useEffect(() => {
    // Fetch ingredients from API (dummy data for now)
    setIngredients([
      { id: 1, name: "Rose Oil", allergens: ["Linalool", "Citronellol"] },
      { id: 2, name: "Lavender Oil", allergens: ["Geraniol"] },
    ]);
  }, []);

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Ingredients
      </Typography>
      <Button variant="contained" color="primary" sx={{ mb: 2 }}>
        Add New Ingredient
      </Button>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Name</TableCell>
              <TableCell>Allergens</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {ingredients.map((ingredient) => (
              <TableRow key={ingredient.id}>
                <TableCell>{ingredient.id}</TableCell>
                <TableCell>{ingredient.name}</TableCell>
                <TableCell>{ingredient.allergens.join(", ")}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
};

export default IngredientsPage;
