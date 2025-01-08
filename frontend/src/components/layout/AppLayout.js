import React from "react";
import { Box, CssBaseline, Toolbar } from "@mui/material";
import Header from "./Header";
import Sidebar from "./Sidebar";

const AppLayout = ({ children }) => {

  return (
    <Box sx={{ display: "flex" }}>
      <CssBaseline />
      {/* Header */}
      <Header />
      {/* Sidebar */}
      <Sidebar />
      {/* Main Content */}
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: 3,
          bgcolor: "background.default",
          minHeight: "100vh",
        }}
      >
        <Toolbar /> {/* Adds space below the fixed header */}
        {children}
      </Box>
    </Box>
  );
};

export default AppLayout;
