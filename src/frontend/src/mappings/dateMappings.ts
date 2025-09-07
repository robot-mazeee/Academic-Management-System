export const formatDate = (dateString?: string) => {
  if (!dateString) return ''
  
  const date = new Date(dateString)
  date.setDate(date.getDate() + 1)

  return date.toLocaleDateString('pt-PT', {
    year: 'numeric',
    month: 'short',
    day: '2-digit',
  })
}

export default function formatDateTime(dateString?: string) {
  if (!dateString) return ''
  return new Date(dateString).toLocaleString('pt-PT', {
    year: 'numeric',
    month: 'short',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
  })
}