import React, { useState } from "react";
import {
  Box,
  Button,
  TextField,
  Typography,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  IconButton,
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";

const AllergensPage = () => {
  const [allergens, setAllergens] = useState([]); // Mock allergens data
  const [formData, setFormData] = useState({ name: "", maxConcentration: "" }); // Form state
  const [editingId, setEditingId] = useState(null); // Track which allergen is being edited

  // Handle form input changes
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  // Handle form submission for add/edit
  const handleSubmit = (e) => {
    e.preventDefault();
    if (editingId !== null) {
      // Update allergen in the list
      setAllergens((prev) =>
        prev.map((allergen) =>
          allergen.id === editingId
            ? { ...allergen, ...formData }
            : allergen
        )
      );
    } else {
      // Add a new allergen
      const newAllergen = {
        id: Date.now(), // Unique ID based on timestamp
        name: formData.name,
        maxConcentration: formData.maxConcentration,
      };
      setAllergens((prev) => [...prev, newAllergen]);
    }

    // Reset form
    setFormData({ name: "", maxConcentration: "" });
    setEditingId(null);
  };

  // Handle edit
  const handleEdit = (allergen) => {
    setFormData({ name: allergen.name, maxConcentration: allergen.maxConcentration });
    setEditingId(allergen.id);
  };

  // Handle delete
  const handleDelete = (id) => {
    setAllergens((prev) => prev.filter((allergen) => allergen.id !== id));
  };

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" gutterBottom>
        Manage Allergens
      </Typography>

      {/* Form */}
      <Paper sx={{ p: 3, mb: 3 }}>
        <form onSubmit={handleSubmit}>
          <TextField
            label="Name"
            name="name"
            value={formData.name}
            onChange={handleInputChange}
            fullWidth
            margin="normal"
            required
          />
          <TextField
            label="Max Concentration (%)"
            name="maxConcentration"
            value={formData.maxConcentration}
            onChange={handleInputChange}
            fullWidth
            margin="normal"
            type="number"
            inputProps={{ step: "0.01", min: "0" }}
            required
          />
          <Button type="submit" variant="contained" color="primary" fullWidth>
            {editingId ? "Update Allergen" : "Add Allergen"}
          </Button>
        </form>
      </Paper>

      {/* Table */}
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Name</TableCell>
              <TableCell>Max Concentration</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {allergens.length > 0 ? (
              allergens.map((allergen) => (
                <TableRow key={allergen.id}>
                  <TableCell>{allergen.id}</TableCell>
                  <TableCell>{allergen.name}</TableCell>
                  <TableCell>{allergen.maxConcentration}%</TableCell>
                  <TableCell>
                    <IconButton
                      color="primary"
                      onClick={() => handleEdit(allergen)}
                    >
                      <EditIcon />
                    </IconButton>
                    <IconButton
                      color="error"
                      onClick={() => handleDelete(allergen.id)}
                    >
                      <DeleteIcon />
                    </IconButton>
                  </TableCell>
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={4} align="center">
                  No allergens available
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
};

export default AllergensPage;
