import React from 'react';
import AllergenList from "@/src/components/AllergenList";

const AllergensPage: React.FC = () => {
    return (
        <div className="p-4">
            <h1 className="text-2xl font-bold">Allergens</h1>
            <AllergenList />
        </div>
    );
};

export default AllergensPage;