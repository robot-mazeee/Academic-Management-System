<template>
  <div class="calendar full-width">
    <header class="calendar-header">
      <button @click="prevMonth" aria-label="Previous month">‹</button>
      <div class="month-label">{{ format(current, 'MMMM yyyy') }}</div>
      <button @click="nextMonth" aria-label="Next month">›</button>
    </header>

    <div class="weekdays">
      <div v-for="d in weekdays" :key="d" class="weekday">{{ d }}</div>
    </div>

    <div class="days-grid">
      <div
        v-for="day in calendarDays"
        :key="day.key"
        :class="['day-cell', { 'other-month': !day.inCurrentMonth, today: isToday(day.date) }]"
        :style="{ backgroundColor: props.dayHighlights[isoKey(day.date)] || '' }"
        @click="selectDay(day.date)"
      >
        <div class="date-number">{{ day.date.getDate() }}</div>

        <div class="event-dots">
          <span
            v-for="(ev, idx) in (eventsByDate[isoKey(day.date)] || []).slice(0, maxDots)"
            :key="ev.id || idx"
            class="dot"
            :title="ev.title"
            :style="{ backgroundColor: ev.color || '#3b82f6' }"></span>
          <span v-if="(eventsByDate[isoKey(day.date)] || []).length > maxDots" class="more">+
            {{ (eventsByDate[isoKey(day.date)] || []).length - maxDots }}</span>
        </div>

        <div v-if="selectedDate && isoKey(selectedDate) === isoKey(day.date)" class="day-popup">
          <strong>{{ format(day.date, 'PPP') }}</strong>
          <ul class="popup-list">
            <li v-for="ev in eventsByDate[isoKey(day.date)]" :key="ev.id">
              <span class="swatch" :style="{ backgroundColor: ev.color || '#3b82f6' }"></span>
              <span class="title">{{ ev.title }}</span>
            </li>
            <li v-if="!(eventsByDate[isoKey(day.date)] && eventsByDate[isoKey(day.date)].length)">No events</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue'

const props = defineProps({
  events: { type: Array, default: () => [] },
  weekStart: { type: Number, default: 1 },
  dayHighlights: { type: Object, default: () => ({}) }
})

const current = ref(new Date())
const selectedDate = ref(null)
const maxDots = 3

function startOfMonth(d){ return new Date(d.getFullYear(), d.getMonth(), 1) }
function endOfMonth(d){ return new Date(d.getFullYear(), d.getMonth()+1, 0) }
function addMonths(d, n){ return new Date(d.getFullYear(), d.getMonth()+n, 1) }
function isSameDay(a,b){ return a && b && a.getFullYear()===b.getFullYear() && a.getMonth()===b.getMonth() && a.getDate()===b.getDate() }
function isToday(d){ const t = new Date(); return isSameDay(d, t) }
function isoKey(d){ return d.toISOString().slice(0,10) }

function format(d, fmt){
  if(!d) return ''
  const opts = { year: 'numeric', month: 'long', day: 'numeric' }
  if(fmt === 'MMMM yyyy') return d.toLocaleString(undefined, { month: 'long', year: 'numeric' })
  if(fmt === 'PPP') return d.toLocaleString(undefined, opts)
  return d.toLocaleDateString()
}

const weekdays = computed(() => {
  const base = ['Sun','Mon','Tue','Wed','Thu','Fri','Sat']
  if(props.weekStart === 0) return base
  return [...base.slice(1), base[0]]
})

const eventsByDate = computed(() => {
  const map = {}
  for(const ev of props.events){
    let d = ev.date instanceof Date ? ev.date : new Date(ev.date)
    if(isNaN(d)) continue
    const k = isoKey(d)
    if(!map[k]) map[k]=[]
    map[k].push(ev)
  }
  return map
})

const calendarDays = computed(() => {
  const first = startOfMonth(current.value)
  const last = endOfMonth(current.value)

  const offset = (first.getDay() - props.weekStart + 7) % 7
  const totalCells = 42
  const days = []
  const startDate = new Date(first)
  startDate.setDate(first.getDate() - offset)

  for(let i=0;i<totalCells;i++){
    const d = new Date(startDate)
    d.setDate(startDate.getDate() + i)
    days.push({ date: d, inCurrentMonth: d.getMonth() === current.value.getMonth(), key: isoKey(d) + '-' + i })
  }
  return days
})

function prevMonth(){ current.value = addMonths(current.value, -1); selectedDate.value = null }
function nextMonth(){ current.value = addMonths(current.value, 1); selectedDate.value = null }
function selectDay(d){ if(selectedDate.value && isSameDay(selectedDate.value, d)) selectedDate.value = null; else selectedDate.value = d }

watch(()=>props.events, ()=>{}, { deep:true })
</script>

<style scoped>
.calendar{ border: 1px solid #e5e7eb; border-radius: 8px; padding: 8px; font-family: system-ui, -apple-system, 'Segoe UI', Roboto, 'Helvetica Neue', Arial; background:white }
.calendar.full-width{ width: 100%; max-width: 100%; }
.calendar-header{ display:flex; align-items:center; justify-content:space-between; padding:4px 8px }
.calendar-header button{ background:none; border: none; font-size:18px; cursor:pointer }
.month-label{ font-weight:600 }
.weekdays{ display:grid; grid-template-columns:repeat(7,1fr); text-align:center; font-size:12px; color:#6b7280; margin-top:6px }
.days-grid{ display:grid; grid-template-columns:repeat(7,1fr); gap:6px; margin-top:6px }
.day-cell{ position:relative; min-height:64px; padding:6px; border-radius:6px; background:white; cursor:pointer; border:1px solid #f3f4f6 }
.day-cell.other-month{ opacity:0.45 }
.day-cell.today{ box-shadow: inset 0 0 0 1px rgba(59,130,246,0.15) }
.date-number{ font-size:13px; font-weight:600 }
.event-dots{ position:absolute; left:6px; bottom:6px; display:flex; gap:4px; align-items:center }
.dot{ width:10px; height:10px; border-radius:50%; display:inline-block }
.more{ font-size:11px; color:#374151; background:#f3f4f6; border-radius:4px; padding:2px 4px }
.day-popup{ position:absolute; left:6px; top:28px; z-index:20; background:white; border:1px solid #e5e7eb; padding:8px; border-radius:6px; width:200px; box-shadow:0 6px 16px rgba(15,23,42,0.08) }
.popup-list{ list-style:none; margin:8px 0 0 0; padding:0 }
.popup-list li{ display:flex; align-items:center; gap:8px; padding:4px 0 }
.swatch{ width:12px; height:12px; border-radius:3px; display:inline-block }
.title{ font-size:13px }
</style>
