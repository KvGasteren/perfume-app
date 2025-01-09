import React from 'react';
import FormulaList from '../../components/FormulaList';

const FormulasPage: React.FC = () => {
    return (
        <div className="p-4">
            <h1 className="text-2xl font-bold">Formulas</h1>
            <FormulaList />
        </div>
    );
};

export default FormulasPage;