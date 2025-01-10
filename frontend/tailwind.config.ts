import type { Config } from "tailwindcss";

export default {
  content: [
    "./src/components/**/*.{js,ts,jsx,tsx}",
    "./src/pages/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: "#209a7c", // Peacock green-blue
        secondary: "#D97742", // Rust orange
        // Optional: Add lighter/darker shades
        "primary-light": "#7ecfbc",
        "primary-dark": "#136B6B",
        "secondary-light": "#E49C71",
        "secondary-dark": "#A6522B",
      },
    },
  },
  plugins: [],
} satisfies Config;
