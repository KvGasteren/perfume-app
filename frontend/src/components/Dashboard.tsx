import React from "react";

const Dashboard: React.FC = () => {
    return (
        <div className="bg-gray-100 min-h-screen">
            {/* Hero Section */}
            <section className="hero bg-gradient-to-r from-indigo-500 to-purple-500 text-white p-16 text-center">
                <h1 className="text-4xl font-extrabold mb-4">Welcome to Perfume App</h1>
                <p className="text-lg mb-6">Your hub for managing formulas and ingredients.</p>
                <button className="bg-white text-indigo-500 font-semibold py-2 px-4 rounded shadow hover:bg-gray-100">
                    Get Started
                </button>
            </section>

            {/* Features Section */}
            <section className="features grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 p-8 container mx-auto">
                <div className="card bg-white shadow rounded p-6">
                    <h2 className="text-xl font-bold mb-2">Manage Formulas</h2>
                    <p>Keep track of all your formulas in one place.</p>
                </div>
                <div className="card bg-white shadow rounded p-6">
                    <h2 className="text-xl font-bold mb-2">Track Ingredients</h2>
                    <p>Manage ingredient details and concentrations effortlessly.</p>
                </div>
                <div className="card bg-white shadow rounded p-6">
                    <h2 className="text-xl font-bold mb-2">Monitor Allergens</h2>
                    <p>Stay compliant by tracking allergens in your ingredients.</p>
                </div>
            </section>
        </div>
    );
};

export default Dashboard;
