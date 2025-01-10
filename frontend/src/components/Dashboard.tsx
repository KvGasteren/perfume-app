import React from "react";
import Link from "next/link";

const Dashboard: React.FC = () => {
    return (
        <div className="bg-gray-100 min-h-screen">
            {/* Hero Section */}
            <section className="hero bg-gradient-to-r from-primary-dark to-primary text-white p-8 md:p-16 text-center">
                <h1 className="text-2xl md:text-4xl font-extrabold mb-4">Welcome to Perfume App</h1>
                <p className="text-base md:text-lg mb-6">
                    Your hub for managing formulas and ingredients.
                </p>
                <Link href="/formulas">
                    <button className="bg-white text-secondary-dark font-semibold py-2 px-4 rounded shadow hover:bg-gray-100 transition duration-300">
                        Get Started
                    </button>
                </Link>
            </section>

            {/* Features Section */}
            <section className="features grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 sm:gap-6 p-4 sm:p-8 container mx-auto">
                <div className="card bg-white shadow rounded p-4 sm:p-6">
                    <h2 className="text-lg md:text-xl font-bold mb-2">Manage Formulas</h2>
                    <p>Keep track of all your formulas in one place.</p>
                    <Link href="/formulas" className="text-primary-dark font-semibold mt-4 block hover:underline">
                            Learn More →
                    </Link>
                </div>
                <div className="card bg-white shadow rounded p-4 sm:p-6">
                    <h2 className="text-lg md:text-xl font-bold mb-2">Track Ingredients</h2>
                    <p>Manage ingredient details and concentrations effortlessly.</p>
                    <Link href="/ingredients" className="text-primary-dark font-semibold mt-4 block hover:underline">
                            Learn More →
                    </Link>
                </div>
                <div className="card bg-white shadow rounded p-4 sm:p-6">
                    <h2 className="text-lg md:text-xl font-bold mb-2">Monitor Allergens</h2>
                    <p>Stay compliant by tracking allergens in your ingredients.</p>
                    <Link href="/allergens" className="text-primary-dark font-semibold mt-4 block hover:underline">
                            Learn More →
                    </Link>
                </div>
            </section>
        </div>
    );
};

export default Dashboard;
