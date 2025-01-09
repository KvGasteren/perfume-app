import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { getIngredients } from "../services/api";

export const fetchIngredients = createAsyncThunk(
    "ingredients/fetchIngredients",
    async () => {
        const response = await getIngredients();
        return response.data;
    }
);

const ingredientsSlice = createSlice({
    name: "ingredients",
    initialState: { ingredients: [], status: "idle" },
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(fetchIngredients.pending, (state) => {
                state.status = "loading";
            })
            .addCase(fetchIngredients.fulfilled, (state, action) => {
                state.status = "succeeded";
                state.ingredients = action.payload;
            })
            .addCase(fetchIngredients.rejected, (state) => {
                state.status = "failed";
            });
    },
});

export default ingredientsSlice.reducer;
