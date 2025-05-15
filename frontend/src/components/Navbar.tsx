import React, { useState } from "react";
import Link from "next/link";
import Image from "next/image";

const Navbar: React.FC = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  return (
    <header className="bg-white shadow p-4">
      <nav className="max-w-4xl mx-auto flex justify-between items-center">
        <Link href={"/"} className="flex items-center space-x-2">
          <Image src="/logo.png" alt="Perfume Logo" width={32} height={32} />
          <h1 className="text-xl font-bold text-primary-dark">Perfume App</h1>
        </Link>
        <button
          className="block md:hidden text-gray-700 focus:outline-none"
          onClick={toggleMenu}
        >
          <svg
            className="w-6 h-6"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d={
                isMenuOpen ? "M6 18L18 6M6 6l12 12" : "M4 6h16M4 12h16M4 18h16"
              }
            />
          </svg>
        </button>
        <ul
          className={`md:flex md:space-x-6 md:items-center ${
            isMenuOpen ? "block" : "hidden"
          } absolute md:static top-16 left-0 w-full bg-white md:w-auto md:bg-transparent shadow-lg md:shadow-none`}
        >
          <li className="border-b md:border-none">
            <Link
              href="/formulas"
              className="block text-gray-700 hover:text-primary-dark transition-colors p-4 md:p-0"
            >
              Formulas
            </Link>
          </li>
          <li className="border-b md:border-none">
            <Link
              href="/ingredients"
              className="block text-gray-700 hover:text-primary-dark transition-colors p-4 md:p-0"
            >
              Ingredients
            </Link>
          </li>
          <li>
            <Link
              href="/allergens"
              className="block text-gray-700 hover:text-primary-dark transition-colors p-4 md:p-0"
            >
              Allergens
            </Link>
          </li>
        </ul>
      </nav>
    </header>
  );
};

export default Navbar;
