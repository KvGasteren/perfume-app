import React from "react";
import { Grid2 as Grid, Paper, Typography } from "@mui/material";

const DashboardPage = () => {
  return (
    <Grid container spacing={3}>
      {/* Perfume Summary */}
      <Grid xs={12} md={4}>
        <Paper elevation={3} sx={{ p: 2 }}>
          <Typography variant="h6">Total Ingredients</Typography>
          <Typography variant="h3" color="primary">
            45
          </Typography>
        </Paper>
      </Grid>

      {/* Allergen Summary */}
      <Grid xs={12} md={4}>
        <Paper elevation={3} sx={{ p: 2 }}>
          <Typography variant="h6">Total Allergens</Typography>
          <Typography variant="h3" color="error">
            12
          </Typography>
        </Paper>
      </Grid>

      {/* Formulas Summary */}
      <Grid xs={12} md={4}>
        <Paper elevation={3} sx={{ p: 2 }}>
          <Typography variant="h6">Total Formulas</Typography>
          <Typography variant="h3" color="secondary">
            8
          </Typography>
        </Paper>
      </Grid>

      {/* Additional sections */}
      <Grid xs={12}>
        <Paper elevation={3} sx={{ p: 3 }}>
          <Typography variant="h5">Welcome to Perfume App</Typography>
          <Typography variant="body1">
            Here you can manage ingredients, formulas, and allergens to create
            amazing perfumes. Use the navigation menu to get started.
          </Typography>
        </Paper>
      </Grid>
    </Grid>
  );
};

export default DashboardPage;
