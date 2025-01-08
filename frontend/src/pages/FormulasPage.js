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

const FormulasPage = () => {
  const [formulas, setFormulas] = useState([]);

  useEffect(() => {
    // Fetch formulas from API (dummy data for now)
    setFormulas([
      {
        id: 1,
        name: "Romantic Rose",
        ingredients: ["Rose Oil", "Sandalwood Oil"],
      },
      {
        id: 2,
        name: "Calm Lavender",
        ingredients: ["Lavender Oil", "Bergamot Oil"],
      },
    ]);
  }, []);

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Formulas
      </Typography>
      <Button variant="contained" color="primary" sx={{ mb: 2 }}>
        Add New Formula
      </Button>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Name</TableCell>
              <TableCell>Ingredients</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {formulas.map((formula) => (
              <TableRow key={formula.id}>
                <TableCell>{formula.id}</TableCell>
                <TableCell>{formula.name}</TableCell>
                <TableCell>{formula.ingredients.join(", ")}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
};

export default FormulasPage;
