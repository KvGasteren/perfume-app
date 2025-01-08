import React from "react";
import { Box, Typography } from "@mui/material";

const Footer = () => {
  return (
    <Box
      component="footer"
      sx={{
        p: 2,
        textAlign: "center",
        bgcolor: "background.paper",
        mt: "auto",
      }}
    >
      <Typography variant="body2" color="text.secondary">
        © 2024 Perfume App. All rights reserved.
      </Typography>
    </Box>
  );
};

export default Footer;
