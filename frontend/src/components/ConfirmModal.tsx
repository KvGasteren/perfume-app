// src/components/ConfirmModal.tsx
import React from "react";

interface ConfirmModalProps {
    isOpen: boolean;
    title: string;
    message: string;
    onConfirm: () => void;
    onCancel: () => void;
    error?: string;
}

const ConfirmModal: React.FC<ConfirmModalProps> = ({ isOpen, title, message, error, onConfirm, onCancel }) => {
    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
            <div className="bg-white rounded-xl shadow-lg p-6 max-w-sm w-full">
                <h2 className="text-lg font-semibold mb-2">{title}</h2>
                <p className="mb-4 text-gray-700">{message}</p>
                {error && (
                  <p className="text-sm text-red-600 mb-4 bg-red-50 p-2 rounded border border-red-200">
                    {error}
                  </p>
                )}
                <div className="flex justify-end space-x-4">
                    <button onClick={onCancel} className="px-4 py-2 bg-gray-100 rounded hover:bg-gray-200">
                        Cancel
                    </button>
                    <button onClick={onConfirm} className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600">
                        Delete
                    </button>
                </div>
            </div>
        </div>
    );
};

export default ConfirmModal;
