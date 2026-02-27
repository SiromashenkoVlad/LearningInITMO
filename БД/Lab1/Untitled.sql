CREATE TABLE [People] (
  [id] integer PRIMARY KEY IDENTITY(1, 1),
  [name] character(200) NOT NULL,
  [surname] character(200) NOT NULL,
  [id_city] integer NOT NULL,
  [control_level] integer NOT NULL DEFAULT (0),
  [role] boolean DEFAULT (0)
)
GO

CREATE TABLE [City] (
  [id] integer PRIMARY KEY IDENTITY(1, 1),
  [name] character(200) NOT NULL
)
GO

CREATE TABLE [Location] (
  [id] integer PRIMARY KEY IDENTITY(1, 1),
  [name] character(200) NOT NULL,
  [id_city] integer NOT NULL,
  [latitude] numeric(10) NOT NULL,
  [longtitude] numeric(10) NOT NULL
)
GO

CREATE TABLE [Powers] (
  [id] integer PRIMARY KEY IDENTITY(1, 1),
  [name] character(100) NOT NULL,
  [level_accept] integer NOT NULL,
  [location_id] integer NOT NULL
)
GO

CREATE TABLE [Machines] (
  [id] integer PRIMARY KEY IDENTITY(1, 1),
  [name] character(200) NOT NULL,
  [city_id] integer NOT NULL,
  [location_id] integer NOT NULL,
  [operation_id] integer NOT NULL,
  [level_accept] integer NOT NULL DEFAULT (2),
  [endurance] integer NOT NULL DEFAULT (100),
  [is_it_infrastructure] boolean NOT NULL DEFAULT (0)
)
GO

CREATE TABLE [Operations] (
  [id] integer PRIMARY KEY IDENTITY(1, 1),
  [location_id] integer NOT NULL,
  [influence] integer NOT NULL DEFAULT (10)
)
GO

ALTER TABLE [People] ADD FOREIGN KEY ([id_city]) REFERENCES [City] ([id])
GO

ALTER TABLE [Machines] ADD FOREIGN KEY ([city_id]) REFERENCES [City] ([id])
GO

ALTER TABLE [Location] ADD FOREIGN KEY ([id_city]) REFERENCES [City] ([id])
GO

ALTER TABLE [Machines] ADD FOREIGN KEY ([location_id]) REFERENCES [Location] ([id])
GO

ALTER TABLE [Operations] ADD FOREIGN KEY ([id]) REFERENCES [Machines] ([operation_id])
GO

ALTER TABLE [Operations] ADD FOREIGN KEY ([location_id]) REFERENCES [Location] ([id])
GO

CREATE TABLE [Powers_Location] (
  [Powers_location_id] integer,
  [Location_id] integer,
  PRIMARY KEY ([Powers_location_id], [Location_id])
);
GO

ALTER TABLE [Powers_Location] ADD FOREIGN KEY ([Powers_location_id]) REFERENCES [Powers] ([location_id]);
GO

ALTER TABLE [Powers_Location] ADD FOREIGN KEY ([Location_id]) REFERENCES [Location] ([id]);
GO

