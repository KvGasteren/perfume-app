import React from "react";

const Navbar: React.FC = () => {
    return (
        <header className="bg-white shadow p-4">
            <nav className="container mx-auto flex justify-between items-center">
                <h1 className="text-xl font-bold text-indigo-600">Perfume App</h1>
                <ul className="flex space-x-6">
                    <li>
                        <a href="/" className="text-gray-700 hover:text-indigo-500 transition-colors">
                            Home
                        </a>
                    </li>
                    <li>
                        <a href="/formulas" className="text-gray-700 hover:text-indigo-500 transition-colors">
                            Formulas
                        </a>
                    </li>
                    <li>
                        <a href="/ingredients" className="text-gray-700 hover:text-indigo-500 transition-colors">
                            Ingredients
                        </a>
                    </li>
                    <li>
                        <a href="/reports" className="text-gray-700 hover:text-indigo-500 transition-colors">
                            Reports
                        </a>
                    </li>
                </ul>
            </nav>
        </header>
    );
};

export default Navbar;
