import React from "react";
import { AppProps } from "next/app";
import Navbar from "../components/Navbar";
import "../styles/global.css";
import Layout from "@/src/components/Layout";

const MyApp: React.FC<AppProps> = ({ Component, pageProps }) => {
    return (
        <Layout>
            <Navbar />
            <Component {...pageProps} />
        </Layout>
    );
};

export default MyApp;
