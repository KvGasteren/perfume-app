import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { getFormulas } from '../services/api';

export const fetchFormulas = createAsyncThunk('formulas/fetchFormulas', async () => {
    const response = await getFormulas();
    return response.data;
});

const formulasSlice = createSlice({
    name: 'formulas',
    initialState: { formulas: [], status: 'idle' },
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(fetchFormulas.pending, (state) => {
                state.status = 'loading';
            })
            .addCase(fetchFormulas.fulfilled, (state, action) => {
                state.status = 'succeeded';
                state.formulas = action.payload;
            })
            .addCase(fetchFormulas.rejected, (state) => {
                state.status = 'failed';
            });
    },
});

export default formulasSlice.reducer;