package com.example.spconvertidor2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private String[] opcionesConversion = {"Medida", "Temperatura", "Peso", "Volumen"};
    private String[] opcionesMedida = {"Pulgadas", "Pies", "Centímetros", "Metros"};
    private String[] opcionesTemperatura = {"Fahrenheit", "Celsius", "Kelvin"};
    private String[] opcionesVolumen = {"Litros", "Galones", "Mililitros"};
    private String[] opcionesPeso = {"Kilogramos", "Onza", "Libra"};


    private Spinner spinnerConversion;
    private Spinner spinnerConvertirDe;
    private Spinner spinnerConvertirA;
    private EditText etDatoIngresado;
    private TextView tvResultado;
    private Button btnConvertir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerConversion = findViewById(R.id.elegirConversion);
        spinnerConvertirDe = findViewById(R.id.convertirDe);
        spinnerConvertirA = findViewById(R.id.convertirA);
        etDatoIngresado = findViewById(R.id.EtdatoIngresado);
        tvResultado = findViewById(R.id.tvresultado);
        btnConvertir = findViewById(R.id.btnConvertir);

        ArrayAdapter<String> adapterConversion = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opcionesConversion);
        spinnerConversion.setAdapter(adapterConversion);

        ArrayAdapter<String> adapterMedida = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opcionesMedida);
        ArrayAdapter<String> adapterTemperatura = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opcionesTemperatura);
        ArrayAdapter<String> adapterVolumen = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opcionesVolumen);
        ArrayAdapter<String> adapterPeso = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opcionesPeso);

        spinnerConversion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = parent.getItemAtPosition(position).toString();

                if (seleccion.equals("Medida")) {
                    spinnerConvertirDe.setAdapter(adapterMedida);
                    spinnerConvertirA.setAdapter(adapterMedida);
                } else if (seleccion.equals("Temperatura")) {
                    spinnerConvertirDe.setAdapter(adapterTemperatura);
                    spinnerConvertirA.setAdapter(adapterTemperatura);
                } else if (seleccion.equals("Peso")) {
                    spinnerConvertirDe.setAdapter(adapterPeso);
                    spinnerConvertirA.setAdapter(adapterPeso);
                } else if (seleccion.equals("Volumen")) {
                    spinnerConvertirDe.setAdapter(adapterVolumen);
                    spinnerConvertirA.setAdapter(adapterVolumen);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

        btnConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String datoString = etDatoIngresado.getText().toString();
                if (datoString.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Ingrese un valor", Toast.LENGTH_SHORT).show();
                    return;
                }
                double dato = Double.parseDouble(datoString);
                String unidadDesde = spinnerConvertirDe.getSelectedItem().toString();
                String unidadHacia = spinnerConvertirA.getSelectedItem().toString();

                double resultado;

                if (spinnerConversion.getSelectedItem().toString().equals("Peso")) {
                    resultado = convertirPeso(dato, unidadDesde, unidadHacia);
                } else if (spinnerConversion.getSelectedItem().toString().equals("Temperatura")) {
                    resultado = convertirTemperatura(dato, unidadDesde, unidadHacia);
                }else if (spinnerConversion.getSelectedItem().toString().equals("Volumen")) {
                    resultado = convertirVolumen(dato, unidadDesde, unidadHacia);
                }else if (spinnerConversion.getSelectedItem().toString().equals("Medida")) {
                    resultado = convertirMedida(dato, unidadDesde, unidadHacia);
                }else {
                    Toast.makeText(MainActivity.this, "Seleccione un tipo de conversión", Toast.LENGTH_SHORT).show();
                    return;
                }

                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                String resultadoFormateado = decimalFormat.format(resultado);

                String mensaje = datoString + " " + unidadDesde + " son " + resultadoFormateado + " " + unidadHacia;
                tvResultado.setText(mensaje);
            }

        });

    }


    private double convertirTemperatura(double temperatura, String unidadDesde, String unidadHacia) {
        if (unidadDesde.equals("Celsius")) {
            if (unidadHacia.equals("Fahrenheit")) {
                return temperatura * 1.8 + 32;
            } else if (unidadHacia.equals("Kelvin")) {
                return temperatura + 273.15;
            }
        } else if (unidadDesde.equals("Fahrenheit")) {
            if (unidadHacia.equals("Celsius")) {
                return (temperatura - 32) / 1.8;
            } else if (unidadHacia.equals("Kelvin")) {
                return (temperatura + 459.67) * 5 / 9;
            }
        } else if (unidadDesde.equals("Kelvin")) {
            if (unidadHacia.equals("Celsius")) {
                return temperatura - 273.15;
            } else if (unidadHacia.equals("Fahrenheit")) {
                return temperatura * 1.8 - 459.67;
            }
        }
        return temperatura;
    }
    private double convertirPeso(double peso, String unidadDesde, String unidadHacia) {
        if (unidadDesde.equals("Kilogramos")) {
            if (unidadHacia.equals("Onza")) {
                return peso * 35.2739619;
            } else if (unidadHacia.equals("Libra")) {
                return peso * 2.20462262;
            }
        } else if (unidadDesde.equals("Onza")) {
            if (unidadHacia.equals("Kilogramos")) {
                return peso / 35.2739619;
            } else if (unidadHacia.equals("Libra")) {
                return peso / 16;
            }
        } else if (unidadDesde.equals("Libra")) {
            if (unidadHacia.equals("Kilogramos")) {
                return peso / 2.20462262;
            } else if (unidadHacia.equals("Onza")) {
                return peso * 16;
            }
        }
        return peso;
    }

    private double convertirVolumen(double volumen, String unidadDesde, String unidadHacia) {
        if (unidadDesde.equals("Litros")) {
            if (unidadHacia.equals("Galones")) {
                return volumen / 3.78541;
            } else if (unidadHacia.equals("Mililitros")) {
                return volumen * 1000;
            }
        } else if (unidadDesde.equals("Galones")) {
            if (unidadHacia.equals("Litros")) {
                return volumen * 3.78541;
            } else if (unidadHacia.equals("Mililitros")) {
                return volumen * 3785.41;
            }
        } else if (unidadDesde.equals("Mililitros")) {
            if (unidadHacia.equals("Litros")) {
                return volumen / 1000;
            } else if (unidadHacia.equals("Galones")) {
                return volumen / 3785.41;
            }
        }
        return volumen;
    }

    private double convertirMedida(double medida, String unidadDesde, String unidadHacia) {
        if (unidadDesde.equals("Pulgadas")) {
            if (unidadHacia.equals("Pies")) {
                return medida / 12;
            } else if (unidadHacia.equals("Centímetros")) {
                return medida * 2.54;
            } else if (unidadHacia.equals("Metros")) {
                return medida * 0.0254;
            }
        } else if (unidadDesde.equals("Pies")) {
            if (unidadHacia.equals("Pulgadas")) {
                return medida * 12;
            } else if (unidadHacia.equals("Centímetros")) {
                return medida * 30.48;
            } else if (unidadHacia.equals("Metros")) {
                return medida * 0.3048;
            }
        } else if (unidadDesde.equals("Centímetros")) {
            if (unidadHacia.equals("Pulgadas")) {
                return medida / 2.54;
            } else if (unidadHacia.equals("Pies")) {
                return medida / 30.48;
            } else if (unidadHacia.equals("Metros")) {
                return medida / 100;
            }
        } else if (unidadDesde.equals("Metros")) {
            if (unidadHacia.equals("Pulgadas")) {
                return medida / 0.0254;
            } else if (unidadHacia.equals("Pies")) {
                return medida / 0.3048;
            } else if (unidadHacia.equals("Centímetros")) {
                return medida * 100;
            }
        }
        return medida;
    }
}