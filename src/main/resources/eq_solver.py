import numpy as np
from sympy import *
from sympy.testing.pytest import ignore_warnings

x_min, x_max = list(map(float, input().split()))
y_min, y_max = list(map(float, input().split()))
z_min, z_max = list(map(float, input().split()))
step = float(input())
EQ = input()

x = Symbol("x")
y = Symbol("y")
z = Symbol("z")

f = None
try:
    eq_str = EQ
    f: Equality = parse_expr(eq_str)
except Exception:
    exit(1)
    
if f.has(z) and (f.has(x) or f.has(y)):
    ans = solve(f, z)
    for eq in ans:
        f_eq = lambdify([x, y], eq, "numpy")
        x_val = np.arange(x_min, x_max + step, step)
        y_val = np.arange(y_min, y_max + step, step)
        z_vals = []
        with ignore_warnings(RuntimeWarning):
            if f.has(y):
                for xi in x_val:
                    z_vals.append(f_eq(xi, y_val))
            else:
                for yi in y_val:
                    z_vals.append(f_eq(x_val, yi))

        if f.has(y):
            for (xi, z_val) in zip(x_val, z_vals):
                for (yi, zi) in zip(y_val, z_val):
                    if np.isnan(xi) or np.isnan(yi) or np.isnan(zi):
                        continue
                    if zi < z_min or zi > z_max:
                        continue
                    print(xi, yi, zi)
        else:
            for (yi, z_val) in zip(y_val, z_vals):
                for (xi, zi) in zip(x_val, z_val):
                    if np.isnan(xi) or np.isnan(yi) or np.isnan(zi):
                        continue
                    if zi < z_min or zi > z_max:
                        continue
                    print(xi, yi, zi)

elif f.has(x, y):
    ans = solve(f, y)
    for eq in ans:
        f = lambdify(x, eq, "numpy")
        x_val = np.arange(x_min, x_max + step, step)
        y_val = None
        with ignore_warnings(RuntimeWarning):
            y_val = f(x_val)
        for coord in zip(x_val, y_val):
            if np.isnan(coord[0]) or np.isnan(coord[1]):
                continue
            if coord[1] < y_min or coord[1] > y_max:
                continue
            print(coord[0], coord[1], 0)

else:
    # Unsupported equation
    exit(1)    

exit(0)