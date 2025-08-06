
# 🐳 Installing Docker Desktop on Windows

---

## Step 1: Check Your Windows Version

Docker Desktop works on:

- Windows 10 Pro or Home (version 1903 or newer)
- Windows 11 (any edition)

---

## Step 2: Download Docker Desktop

1. Go to: [https://docs.docker.com/desktop/setup/install/windows-install/](https://docs.docker.com/desktop/setup/install/windows-install/)
2. Click **"Download for Windows"**
3. Save the installer and run it

---

## Step 3: Run the Installer

1. Double-click the downloaded `.exe` file
2. On the install screen, make sure **"WSL 2 backend"** is checked
3. Click **Install**
4. Let Docker Desktop install everything — it may take a few minutes

---

## Step 4: Let Docker Set Up WSL 2

If WSL 2 is not yet installed, Docker will:

- Prompt you to install the **WSL 2 kernel update**
- Open instructions in your browser if needed
- Ask you to **enable Windows features**

---

## Step 5: Restart Your Computer (if prompted)

Sometimes Windows needs a restart after enabling WSL or installing kernel updates.

---

## Step 6: Launch Docker Desktop

1. Search for "Docker Desktop" in the Start Menu
2. Open it — you’ll see the Docker whale 🐳 icon in the taskbar
3. Wait until it says “Docker is running”

---

## Step 7: Verify It’s Working

Open **Command Prompt** or **PowerShell** and run:

```bash
docker run hello-world
```

## Step 8: Run the docker compose file with this command:


```bash
docker compose up
```

This  will start keycloak and postgres on ports 8080 and 5432 on your system.
