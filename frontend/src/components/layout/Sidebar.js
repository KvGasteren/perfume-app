import React from "react";
import {
  Box,
  Drawer,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
} from "@mui/material";
import DashboardIcon from "@mui/icons-material/Dashboard";
import LocalFloristIcon from "@mui/icons-material/LocalFlorist";
import ScienceIcon from "@mui/icons-material/Science";
import CleanHandsIcon from "@mui/icons-material/CleanHands";

const Sidebar = () => {
  const drawerWidth = 240;

  return (
    <Drawer
      variant="permanent"
      sx={{
        width: drawerWidth,
        flexShrink: 0,
        [`& .MuiDrawer-paper`]: {
          width: drawerWidth,
          boxSizing: "border-box",
          marginTop: 8
        },
      }}
    >
      <Box sx={{ overflow: "auto" }}>
        <List>
          <ListItem button="true" component="a" href="/dashboard">
            <ListItemIcon>
              <DashboardIcon />
            </ListItemIcon>
            <ListItemText primary="Dashboard" />
          </ListItem>
          <ListItem button="true" component="a" href="/formulas">
            <ListItemIcon>
              <ScienceIcon />
            </ListItemIcon>
            <ListItemText primary="Formulas" />
          </ListItem>
          <ListItem button="true" component="a" href="/ingredients">
            <ListItemIcon>
              <LocalFloristIcon />
            </ListItemIcon>
            <ListItemText primary="Ingredients" />
          </ListItem>
          <ListItem button="true" component="a" href="/allergens">
            <ListItemIcon>
              <CleanHandsIcon />
            </ListItemIcon>
            <ListItemText primary="Allergens" />
          </ListItem>
        </List>
      </Box>
    </Drawer>
  );
};

export default Sidebar;
