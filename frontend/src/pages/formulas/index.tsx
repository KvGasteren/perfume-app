import React from 'react';
import FormulaList from '../../components/FormulaList';
import { PlusCircleIcon } from '@heroicons/react/16/solid';

const FormulasPage: React.FC = () => {
    return (
        <div className="max-w-4xl mx-auto">
            <h1 className="text-2xl font-bold">Formulas</h1>
            <FormulaList />
            <div className="pr-4 flex justify-end">
                <PlusCircleIcon className="h-6 w-6 text-primary" />
            </div>
        </div>
    );
};

export default FormulasPage;