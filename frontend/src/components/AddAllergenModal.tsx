import React, { useState } from 'react'

interface AddAllergenModalProps {
  isOpen: boolean
  onClose: () => void
  onAdd: (name: string) => void
}

const AddAllergenModal: React.FC<AddAllergenModalProps> = ({ isOpen, onClose, onAdd }) => {
  const [name, setName] = useState('')

  if (!isOpen) return null

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    if (!name.trim()) return
    await onAdd(name.trim())
    setName('')
    onClose()
  }

  return (
    <div className="fixed inset-0 bg-black bg-opacity-40 flex items-center justify-center z-50">
      <div className="bg-white rounded-2xl shadow-xl p-6 w-full max-w-md">
        <h2 className="text-xl font-semibold mb-4">Add Allergen</h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <input
            type="text"
            placeholder="Allergen name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-primary"
          />
          <div className="flex justify-end gap-2">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 rounded-lg bg-gray-200 hover:bg-gray-300"
            >
              Cancel
            </button>
            <button
              type="submit"
              className="px-4 py-2 rounded-lg bg-primary text-white hover:bg-primary/80"
            >
              Add
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}

export default AddAllergenModal
