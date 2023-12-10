import React from 'react'
import ReactDOM from 'react-dom/client'
import '@mantine/core/styles.css';
import App from './App.jsx'
import './index.css'
import { MantineProvider } from '@mantine/core'
import { Notifications } from '@mantine/notifications';
import '@mantine/notifications/styles.css';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <MantineProvider >
      <Notifications/>
      <App/>
    </MantineProvider>
  </React.StrictMode>,
)
