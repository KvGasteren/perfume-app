import type { Config } from "tailwindcss";

export default {
  content: [
    "./src/components/**/*.{js,ts,jsx,tsx}",
    "./src/pages/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: "#d9b165",
        secondary: "#f6e5b4",
        accent: "#422c1c",
        background: "#f7f4f1",
        highlight: "#e3ded1",
        // Optional: Add lighter/darker shades
        "primary-light":"#d9b065",
        "primary-dark": "#b38735",
        "secondary-light":"#f6e6b4",
        "secondary-dark":"#d3be81",
      },
    },
  },
  plugins: [],
} satisfies Config;
