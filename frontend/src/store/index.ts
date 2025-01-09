import { configureStore } from "@reduxjs/toolkit";
import formulasReducer from "./formulasSlice";
import ingredientsReducer from "./ingredientsSlice";

export const store = configureStore({
    reducer: {
        formulas: formulasReducer,
        ingredients: ingredientsReducer,
    },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
