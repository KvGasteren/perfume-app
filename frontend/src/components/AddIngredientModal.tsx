import React, { useState } from 'react'
import { Allergen, IngredientAllergen } from '../types/perfume'

interface AddIngredientModalProps {
  isOpen: boolean
  onClose: () => void
  onAdd: (name: string, allergens: IngredientAllergen[]) => void
  availableAllergens: Allergen[]
}

const AddIngredientModal: React.FC<AddIngredientModalProps> = ({ isOpen, onClose, onAdd, availableAllergens }) => {
  const [name, setName] = useState('')
  const [selectedAllergens, setSelectedAllergens] = useState<IngredientAllergen[]>([])

  if (!isOpen) return null

  const handleAddAllergen = (allergen: Allergen) => {
    if (selectedAllergens.some(a => a.id === allergen.id)) return
    setSelectedAllergens(prev => [...prev, { ...allergen, concentration: 0 }])
  }

  const handleRemoveAllergen = (id: number) => {
    setSelectedAllergens(prev => prev.filter(a => a.id !== id))
  }

  const handleConcentrationChange = (id: number, value: number) => {
    setSelectedAllergens(prev =>
      prev.map(a => (a.id === id ? { ...a, concentration: value } : a))
    )
  }

  const totalConcentration = selectedAllergens.reduce((sum, a) => sum + a.concentration, 0)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    if (!name.trim() || totalConcentration > 100) return
    await onAdd(name.trim(), selectedAllergens)
    setName('')
    setSelectedAllergens([])
    onClose()
  }

  return (
    <div className="fixed inset-0 bg-black bg-opacity-40 flex items-center justify-center z-50">
      <div className="bg-white rounded-2xl shadow-xl p-6 w-full max-w-2xl">
        <h2 className="text-xl font-semibold mb-4">Add Ingredient</h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <input
            type="text"
            placeholder="Ingredient name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-primary"
          />

          <div>
            <h3 className="text-lg font-medium mb-2">Add Allergens</h3>
            <div className="flex flex-wrap gap-2 mb-4">
              {availableAllergens.map(allergen => (
                <button
                  key={allergen.id}
                  type="button"
                  onClick={() => handleAddAllergen(allergen)}
                  className="px-3 py-1 rounded-full bg-gray-100 hover:bg-gray-200"
                >
                  {allergen.name}
                </button>
              ))}
            </div>

            {selectedAllergens.length > 0 && (
              <div className="space-y-2">
                {selectedAllergens.map(a => (
                  <div key={a.id} className="flex items-center gap-2">
                    <span className="w-32 truncate">{a.name}</span>
                    <input
                      type="number"
                      min={0}
                      max={100}
                      step={0.1}
                      value={a.concentration}
                      onChange={(e) => handleConcentrationChange(a.id, parseFloat(e.target.value) || 0)}
                      className="w-24 border border-gray-300 rounded-lg px-2 py-1 focus:outline-none focus:ring-2 focus:ring-primary"
                    />
                    <span>%</span>
                    <button
                      type="button"
                      onClick={() => handleRemoveAllergen(a.id)}
                      className="text-red-500 hover:underline"
                    >
                      Remove
                    </button>
                  </div>
                ))}
              </div>
            )}
          </div>

          <div className="flex justify-between items-center">
            <span className={`text-sm ${totalConcentration > 100 ? 'text-red-500' : 'text-gray-600'}`}>Total: {totalConcentration.toFixed(1)}%</span>
            <div className="flex gap-2">
              <button
                type="button"
                onClick={onClose}
                className="px-4 py-2 rounded-lg bg-gray-200 hover:bg-gray-300"
              >
                Cancel
              </button>
              <button
                type="submit"
                disabled={totalConcentration > 100}
                className="px-4 py-2 rounded-lg bg-primary text-white hover:bg-primary/80 disabled:bg-gray-400"
              >
                Add
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  )
}

export default AddIngredientModal
